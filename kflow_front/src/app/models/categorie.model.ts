import { FormControl, FormGroup } from "@angular/forms";
import { User } from "./user.model";
import { Stage } from "./stage.model";
import { Competition } from "./competition.model";

export interface Categorie {
    id: number;
    name: string;
    users: User[];
    stages: Stage[];
    competition: Competition;
}

export namespace Categorie {
    export function formGroup(categorie?: Categorie) {
        return new FormGroup({
            id: new FormControl(categorie?.id ?? 0, { nonNullable: true }),
            name: new FormControl(categorie?.id ?? 0, { nonNullable: true }),
            users: new FormControl(categorie?.id ?? 0, { nonNullable: true }),
            stages: new FormControl(categorie?.id ?? 0, { nonNullable: true }),
            competition:  new FormControl(categorie?.id ?? 0, { nonNullable: true })
        })
    }
}
