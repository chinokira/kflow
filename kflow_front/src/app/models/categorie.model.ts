import { FormControl, FormGroup } from "@angular/forms";
import { Participant } from "./participant.model";
import { Stage } from "./stage.model";
import { Competition } from "./competition.model";

export interface Categorie {
    id: number;
    name: string;
    participants: Participant[];
    stages: Stage[];
    competition: Competition;
}

export namespace Categorie {
    export function formGroup(categorie?: Categorie) {
        return new FormGroup({
            id: new FormControl(categorie?.id ?? 0, { nonNullable: true }),
            name: new FormControl(categorie?.name ?? '', { nonNullable: true }),
            participants: new FormControl(categorie?.participants ?? [], { nonNullable: true }),
            stages: new FormControl(categorie?.stages ?? [], { nonNullable: true }),
            competition: new FormControl(categorie?.competition ?? null, { nonNullable: true })
        });
    }
}
