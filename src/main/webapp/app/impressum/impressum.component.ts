import { Component, OnInit } from '@angular/core';
import { NavbarService } from 'app/layouts/navbar/navbar.service';
import { FooterService } from 'app/layouts/footer/footer.service';

@Component({
    selector: 'jhi-impressum',
    templateUrl: './impressum.component.html',
    styleUrls: ['impressum.component.scss']
})
export class ImpressumComponent implements OnInit {
    message: string;

    constructor(public nav: NavbarService, public fot: FooterService) {
        this.message = 'ImpressumComponent message';
    }

    ngOnInit() {
        this.nav.hide();
        this.fot.hide();
    }
}
