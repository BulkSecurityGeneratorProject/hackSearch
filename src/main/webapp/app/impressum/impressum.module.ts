import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HackSearchSharedModule } from '../shared';

import { IMPRESSUM_ROUTE, ImpressumComponent } from './';

@NgModule({
    imports: [HackSearchSharedModule, RouterModule.forRoot([IMPRESSUM_ROUTE], { useHash: true })],
    declarations: [ImpressumComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HackSearchAppImpressumModule {}
