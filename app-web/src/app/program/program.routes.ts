import { ProgramDialogComponent, ProgramPopupComponent } from './program-dialog.component';
import { Routes } from '@angular/router';
import { ProgramComponent } from './program.component';

export const programRoutes: Routes = [
  {
    path: 'program',
    component: ProgramComponent,
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'Program Master'
    }
  }
];

export const programPopupRoutes: Routes = [
  {
      path: 'program-new',
      component: ProgramPopupComponent,
      data: {
          authorities: ['ROLE_ADMIN'],
          pageTitle: 'Master Agama'
      },
      // canActivate: [UserRouteAccessService],
      outlet: 'popup'
  },
  {
      path: 'program/:id/edit',
      component: ProgramPopupComponent,
      data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'Regions'
      },
      // canActivate: [UserRouteAccessService],
      outlet: 'popup'
  }
];

