import { FormControl, FormGroup, Validators } from "@angular/forms";

export enum Role {
    USER = 'USER',
    ADMIN = 'ADMIN'
}

export interface User {
    id: number;
    name: string;
    email: string;
    password: string;
    role: Role;
}

export namespace User {
    export function formGroup(user?: User) {
        return new FormGroup({
            id: new FormControl(user?.id ?? 0),
            name: new FormControl(user?.name ?? '', [
                Validators.required
            ]),
            email: new FormControl(user?.email ?? '', [
                Validators.required,
                Validators.email
            ]),
            password: new FormControl(user?.password ?? '', [
                Validators.required,
                Validators.minLength(8)
            ]),
            role: new FormControl(user?.role ?? Role.USER)
        });
    }
}
