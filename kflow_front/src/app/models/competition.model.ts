import { FormControl, FormGroup } from "@angular/forms";
import { Categorie } from "./categorie.model";

export interface Competition {
    id: number;
    startDate: string;
    endDate: string;
    level: string;
    place: string;
    categories?: Categorie[];
}

export namespace Competition {
    export function formGroup(competition?: Competition) {
        return new FormGroup({
            id: new FormControl(competition?.id ?? 0, { nonNullable: true }),
            startDate: new FormControl(competition?.startDate ?? '', { nonNullable: true }),
            endDate: new FormControl(competition?.endDate ?? '', { nonNullable: true }),
            level: new FormControl(competition?.level ?? '', { nonNullable: true }),
            place: new FormControl(competition?.place ?? '', { nonNullable: true })
        })
    }
}
