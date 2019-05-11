import { NgModule, CUSTOM_ELEMENTS_SCHEMA, Component } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HackSearchSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSliderModule } from '@angular/material/slider';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatInputModule } from '@angular/material/input';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatPaginatorModule, MatTableModule } from '@angular/material';
import { MatSelectModule } from '@angular/material/select';
import { ShareButtonModule } from '@ngx-share/button';

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        MatAutocompleteModule,
        MatFormFieldModule,
        MatSliderModule,
        MatSlideToggleModule,
        MatInputModule,
        HackSearchSharedModule,
        RouterModule.forChild([HOME_ROUTE]),
        MatTableModule,
        MatPaginatorModule,
        MatSelectModule,
        ShareButtonModule
    ],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HackSearchHomeModule {}
