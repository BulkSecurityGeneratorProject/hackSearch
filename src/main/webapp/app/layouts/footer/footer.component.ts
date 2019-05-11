import { Component } from '@angular/core';
import { FooterService } from 'app/layouts/footer/footer.service';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html'
})
export class FooterComponent {
    constructor(public fot: FooterService) {}
}
