import { ProgramDialogComponent } from './program-dialog.component';
import { ProgramModalComponent } from './program-modal.component';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { ProgramService } from './program.service';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-program',
  templateUrl: './program.component.html',
  styleUrls: ['./program.component.css']
})
export class ProgramComponent implements OnInit {
  programs: Observable<any>;
  bsModalRef: BsModalRef;

  constructor(
    private programService: ProgramService,
    private modalService: BsModalService) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.programService.getAll().subscribe(data => {
      this.programs = data;
      console.log('programs', data);
    });
  }

  edit(id: number) {
    this.programService.findOne(id).subscribe(data => {
      console.log('data', data);
      const initialState = {
        vm: data,
        title: 'Edit '
      };

      this.bsModalRef = this.modalService.show(ProgramDialogComponent, {initialState});
      this.bsModalRef.content.closeBtnName = 'Close';
    });

  }

  openModalWithComponent() {
    const initialState = {
      list: [
        'Open a modal with component',
        'Pass your data',
        'Do something else',
        '...'
      ],
      title: 'Modal with component'
    };
    this.bsModalRef = this.modalService.show(ProgramModalComponent, {initialState});
    this.bsModalRef.content.closeBtnName = 'Close';
  }

}
