import { FormControl, FormGroup } from "@angular/forms";
import { Categorie } from "./categorie.model";
import { Run } from "./run.model";

export interface Stage {
    id: number;
    nbRun: number;
    rules: string;
    nbCompetitor: number;
    categorie: Categorie;
    runs: Run[];
}

export namespace Stage {
    export function formGroup(stage?: Stage) {
        return new FormGroup({
        id: new FormControl(stage?.id ?? 0, { nonNullable: true }),
        nbRun:  new FormControl(stage?.id ?? 0, { nonNullable: true }),
        rules:  new FormControl(stage?.id ?? 0, { nonNullable: true }),
        nbCompetitor:  new FormControl(stage?.id ?? 0, { nonNullable: true }),
        categorie:  new FormControl(stage?.id ?? 0, { nonNullable: true }),
        runs:  new FormControl(stage?.id ?? 0, { nonNullable: true })
        })
    }
}
