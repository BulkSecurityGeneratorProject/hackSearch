import { Injectable } from '@angular/core';

@Injectable()
export class FooterService {
    visible: boolean;

    constructor() {
        this.visible = true;
    }

    public hide() {
        this.visible = false;
    }

    show() {
        this.visible = true;
    }

    toggle() {
        this.visible = !this.visible;
    }
}
