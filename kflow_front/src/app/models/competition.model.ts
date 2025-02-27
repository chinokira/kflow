import { FormControl, FormGroup } from "@angular/forms";
import { Categorie } from "./categorie.model";

export interface Competition {
    id: number;
    date: string;
    level: string;
    place: string;
    categories: Categorie[];
}

export namespace Competition {
    export function formGroup(competition?: Competition) {
        return new FormGroup({
            id: new FormControl(competition?.id ?? 0, { nonNullable: true }),
            date: new FormControl(competition?.id ?? 0, { nonNullable: true }),
            level: new FormControl(competition?.id ?? 0, { nonNullable: true }),
            place: new FormControl(competition?.id ?? 0, { nonNullable: true })/*,
            categorie:  new FormControl(competition?.id ?? 0, { nonNullable: true })*/
        })
    }
}
