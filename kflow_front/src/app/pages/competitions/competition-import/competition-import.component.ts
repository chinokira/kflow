import { Component } from '@angular/core';
import { ImportService, ImportCompetitionDto } from '../../../services/import.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-competition-import',
  templateUrl: './competition-import.component.html',
  styleUrls: ['./competition-import.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatProgressBarModule
  ]
})
export class CompetitionImportComponent {
  jsonContent: string = '';
  formattedJsonContent: string = '';
  highlightedJson: SafeHtml = '';
  isValidating = false;
  importErrors: Array<{ message: string; line?: number }> = [];
  
  constructor(
    private importService: ImportService,
    private snackBar: MatSnackBar,
    private router: Router,
    private sanitizer: DomSanitizer
  ) {}

  onJsonChange(): void {
    try {
      if (this.jsonContent.trim()) {
        const parsedJson = JSON.parse(this.jsonContent);
        this.formattedJsonContent = JSON.stringify(parsedJson, null, 2);
        this.highlightJson();
        this.validateImport();
      } else {
        this.formattedJsonContent = '';
        this.highlightedJson = '';
        this.importErrors = [];
      }
    } catch (e) {
      this.handleJsonParseError(e as Error);
    }
  }

  onFileSelected(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.jsonContent = e.target?.result as string;
        try {
          const parsedJson = JSON.parse(this.jsonContent);
          this.formattedJsonContent = JSON.stringify(parsedJson, null, 2);
          this.highlightJson();
          this.validateImport();
        } catch (e) {
          this.handleJsonParseError(e as Error);
        }
      };
      reader.readAsText(file);
    }
  }

  private highlightJson(): void {
    const lines = this.formattedJsonContent.split('\n');
    const errorLines = new Set(this.importErrors.map(error => error.line).filter(line => line !== undefined));
    
    const highlightedLines = lines.map((line, index) => {
      const lineNumber = index + 1;
      const isErrorLine = errorLines.has(lineNumber);
      const lineClass = isErrorLine ? 'error-line' : '';
      
      // Coloration syntaxique basique
      const coloredLine = line
        .replace(/(".*?")/g, '<span class="json-string">$1</span>')
        .replace(/\b(true|false|null)\b/g, '<span class="json-keyword">$1</span>')
        .replace(/\b(\d+\.?\d*)\b/g, '<span class="json-number">$1</span>')
        .replace(/({|}|\[|\]|,|:)/g, '<span class="json-punctuation">$1</span>');

      return `<div class="json-line ${lineClass}">${coloredLine}</div>`;
    }).join('');

    this.highlightedJson = this.sanitizer.bypassSecurityTrustHtml(highlightedLines);
  }

  private handleJsonParseError(error: Error): void {
    const errorMessage = error.message;
    let lineNumber: number | undefined;
    let detailedMessage = 'Format JSON invalide';

    const positionMatch = errorMessage.match(/position (\d+)/);
    if (positionMatch) {
      const position = parseInt(positionMatch[1]);
      lineNumber = this.getLineNumberFromPosition(position);
      detailedMessage = this.getDetailedErrorMessage(errorMessage);
    }

    this.importErrors = [{
      message: detailedMessage,
      line: lineNumber
    }];
    this.isValidating = false;
    this.highlightJson();
    this.snackBar.open(detailedMessage + (lineNumber ? ` (ligne ${lineNumber})` : ''), 'Fermer', { duration: 5000 });
  }

  private getLineNumberFromPosition(position: number): number {
    const contentBeforeError = this.jsonContent.substring(0, position);
    return (contentBeforeError.match(/\n/g) || []).length + 1;
  }

  private getDetailedErrorMessage(errorMessage: string): string {
    if (errorMessage.includes('Unexpected token')) {
      const token = errorMessage.match(/Unexpected token (.*?) in/)?.[1];
      return `Caractère inattendu ${token} dans le JSON`;
    }
    if (errorMessage.includes('Unexpected end of JSON input')) {
      return 'JSON incomplet ou mal formaté';
    }
    if (errorMessage.includes('Unexpected string')) {
      return 'Chaîne de caractères inattendue - vérifiez les virgules et les accolades';
    }
    return 'Format JSON invalide - vérifiez la syntaxe';
  }

  validateImport(): void {
    this.isValidating = true;
    this.importErrors = [];
    
    try {
      const importData: ImportCompetitionDto = JSON.parse(this.jsonContent);
      this.importService.validateImport(importData).subscribe({
        next: (errors) => {
          this.importErrors = errors.map(error => ({
            message: error,
            line: this.findLineNumberForError(error)
          }));
          this.highlightJson();
          if (errors.length === 0) {
            this.snackBar.open('Le fichier est valide !', 'Fermer', { duration: 3000 });
          }
        },
        error: (error) => {
          this.snackBar.open('Erreur lors de la validation', 'Fermer', { duration: 3000 });
          console.error('Erreur de validation:', error);
        },
        complete: () => {
          this.isValidating = false;
        }
      });
    } catch (e) {
      this.handleJsonParseError(e as Error);
    }
  }

  private findLineNumberForError(error: string): number | undefined {
    // Exemple de recherche de ligne pour des erreurs spécifiques
    if (error.includes('startDate')) {
      return this.findLineNumberInJson('startDate');
    }
    if (error.includes('endDate')) {
      return this.findLineNumberInJson('endDate');
    }
    if (error.includes('level')) {
      return this.findLineNumberInJson('level');
    }
    if (error.includes('place')) {
      return this.findLineNumberInJson('place');
    }
    if (error.includes('categories')) {
      return this.findLineNumberInJson('categories');
    }
    return undefined;
  }

  private findLineNumberInJson(key: string): number {
    const lines = this.formattedJsonContent.split('\n');
    for (let i = 0; i < lines.length; i++) {
      if (lines[i].includes(`"${key}"`)) {
        return i + 1;
      }
    }
    return 1;
  }

  importCompetition(): void {
    if (this.importErrors.length > 0) {
      this.snackBar.open('Veuillez corriger les erreurs avant d\'importer', 'Fermer', { duration: 3000 });
      return;
    }

    try {
      const importData: ImportCompetitionDto = JSON.parse(this.jsonContent);
      this.importService.importCompetition(importData).subscribe({
        next: (competition) => {
          this.snackBar.open('Compétition importée avec succès !', 'Fermer', { duration: 3000 });
          this.router.navigate(['/competitions', competition.id]);
        },
        error: (error) => {
          this.snackBar.open('Erreur lors de l\'import', 'Fermer', { duration: 3000 });
          console.error('Erreur d\'import:', error);
        }
      });
    } catch (e) {
      this.handleJsonParseError(e as Error);
    }
  }

  getLineNumbers(): string {
    return this.formattedJsonContent
      .split('\n')
      .map((_, i) => i + 1)
      .join('\n');
  }
} 