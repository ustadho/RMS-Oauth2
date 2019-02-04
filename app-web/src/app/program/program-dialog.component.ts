import { Observable } from 'rxjs';
import { ProgramService } from './program.service';
import { ProgramPopupService } from './program-popup.service';
import { ActivatedRoute, Router } from '@angular/router';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Component, OnInit, OnDestroy, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-program-dialog',
  templateUrl: './program-dialog.component.html'
})
export class ProgramDialogComponent implements OnInit {
  title: string;
  closeBtnName: string;
  saveBtnName: string;
  vm: any = {};
  routeSub: any;
  ori: any;
  isSaving: boolean;
  @Output() refreshListEvent: EventEmitter<any> = new EventEmitter();

  constructor(
    private router: Router,
    public bsModalRef: BsModalRef,
    private programService: ProgramService
    ) {}

  ngOnInit(): void {
    this.ori = JSON.parse(JSON.stringify(this.vm));
  }

  public save() {
    console.log('save', this.vm);
    this.programService.save(this.vm, this.ori.id)
    .then(() => {
      this.bsModalRef.hide();
      this.refreshListEvent.emit('refreshProgramList');
    });
  }

}

@Component({
  selector: 'app-program-dialog',
  template: ''
})
export class ProgramPopupComponent implements OnInit, OnDestroy {
  routeSub: any;

  constructor(
    private route: ActivatedRoute,
    private popupService: ProgramPopupService
  ) {}

  ngOnDestroy(): void {
    this.routeSub.unsubscribe();
  }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe((params) => {
        if (params['id']) {
            this.popupService
            .open(ProgramDialogComponent as Component, params['id']);
        } else {
            this.popupService
            .open(ProgramDialogComponent as Component);
        }
    });
  }

}
