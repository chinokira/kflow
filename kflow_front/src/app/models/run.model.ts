import { FormControl, FormGroup } from "@angular/forms";
import { Stage } from "./stage.model";
import { User } from "./user.model";

export interface Run {
    id: number;
    duration: number;
    score: number;
    stage: Stage;
    user: User;
}

export namespace Run {
    export function formGroup(run?: Run) {
        return new FormGroup({
            id: new FormControl(run?.id ?? 0, { nonNullable: true }),
            duration: new FormControl(run?.id ?? 0, { nonNullable: true }),
            score: new FormControl(run?.id ?? 0, { nonNullable: true }),
            stage:  new FormControl(run?.id ?? 0, { nonNullable: true }),
            user:  new FormControl(run?.id ?? 0, { nonNullable: true })
        })
    }
}
