import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HackSearchSharedModule } from '../shared';

import { HI_THERE_ROUTE, HiThereComponent } from './';

@NgModule({
    imports: [HackSearchSharedModule, RouterModule.forRoot([HI_THERE_ROUTE], { useHash: true })],
    declarations: [HiThereComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HackSearchAppHiThereModule {}
