import { BsModalRef } from 'ngx-bootstrap/modal';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-program-modal',
  template: `
  <div class="modal-header">
    <h4 class="modal-title pull-left">{{title}}</h4>
    <button type="button" class="close pull-right" aria-label="Close" (click)="bsModalRef.hide()">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="modal-body">
    <ul *ngIf="list.length">
      <li *ngFor="let item of list">{{item}}</li>
    </ul>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default" (click)="bsModalRef.hide()">{{closeBtnName}}</button>
  </div>
  `
})

export class ProgramModalComponent implements OnInit {
  title: string;
  closeBtnName: string;
  saveBtnName: string;
  list: any[] = [];

  constructor(public bsModalRef: BsModalRef) {}

  ngOnInit(): void {
    this.list.push('PROFIT...');
  }
}
