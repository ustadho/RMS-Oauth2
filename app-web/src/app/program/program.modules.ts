import { FormsModule } from '@angular/forms';
import { ProgramPopupService } from './program-popup.service';
import { ProgramDialogComponent, ProgramPopupComponent } from './program-dialog.component';
import { ProgramModalComponent } from './program-modal.component';
import { ProgramService } from './program.service';
import { ProgramComponent } from './program.component';
import { RouterModule } from '@angular/router';
import { programRoutes, programPopupRoutes } from './program.routes';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

const PROGRAM_STATES = [
  ...programRoutes,
  ...programPopupRoutes
];

@NgModule({
  imports: [
    RouterModule.forRoot(PROGRAM_STATES, {useHash: true}),
    CommonModule,
    FormsModule
  ],
  declarations: [
    ProgramComponent,
    ProgramModalComponent,
    ProgramDialogComponent,
    ProgramPopupComponent
  ],
  entryComponents: [
    ProgramComponent,
    ProgramModalComponent,
    ProgramDialogComponent,
    ProgramPopupComponent
  ],
  providers: [ProgramService, ProgramPopupService]
})

export class ProgramModule {}
