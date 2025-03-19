import { FormControl, FormGroup } from "@angular/forms";
import { Participant } from "./participant.model";
import { Stage } from "./stage.model";

export interface Categorie {
    id: number;
    name: string;
    participants: Participant[];
    stages: Stage[];
    competitionId: number;
}

export namespace Categorie {
    export function formGroup(categorie?: Categorie) {
        return new FormGroup({
            id: new FormControl(categorie?.id ?? 0, { nonNullable: true }),
            name: new FormControl(categorie?.name ?? '', { nonNullable: true }),
            competitionId: new FormControl(categorie?.competitionId ?? 0, { nonNullable: true })
        })
    }
}
