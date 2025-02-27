import { FormControl, FormGroup } from "@angular/forms";
import { Categorie } from "./categorie.model";
import { Run } from "./run.model";

export interface User {
    id: number;
    bibNb: number;
    name: string;
    email: string;
    password: string;
    categories: Categorie[];
    runs: Run[];
}

export namespace User {
    export function formGroup(user?: User) {
        return new FormGroup({
        id: new FormControl(user?.id ?? 0, { nonNullable: true }),
        bibNb: new FormControl(user?.name ?? '', { nonNullable: true }),
        name:  new FormControl(user?.id ?? 0, { nonNullable: true }),
        email:  new FormControl(user?.id ?? 0, { nonNullable: true }),
        password:  new FormControl(user?.id ?? 0, { nonNullable: true }),
        categories:  new FormControl(user?.id ?? 0, { nonNullable: true }),
        runs:  new FormControl(user?.id ?? 0, { nonNullable: true })
        })
    }
}
