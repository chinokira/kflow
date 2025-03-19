import { FormControl, FormGroup } from "@angular/forms";
import { Categorie } from "./categorie.model";
import { Run } from "./run.model";

export interface Participant {
    id: number;
    bibNb: number;
    name: string;
    categories: Categorie[];
    runs: Run[];
}

export namespace Participant {
    export function formGroup(participant?: Participant) {
        return new FormGroup({
            id: new FormControl(participant?.id ?? 0, { nonNullable: true }),
            bibNb: new FormControl(participant?.bibNb ?? 0, { nonNullable: true }),
            name: new FormControl(participant?.name ?? '', { nonNullable: true }),
            categories: new FormControl(participant?.categories ?? [], { nonNullable: true }),
            runs: new FormControl(participant?.runs ?? [], { nonNullable: true })
        });
    }
} 