import { ProgramService } from './program.service';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { Injectable, Component } from '@angular/core';

@Injectable()
export class ProgramPopupService {
  private bsModalRef: BsModalRef;

  constructor(
    private bsModalService: BsModalService,
    private router: Router,
    private service: ProgramService
  ) {}

  open(comp: Component, id?: number | any): Promise<BsModalRef> {
    return new Promise<BsModalRef>((resolve, reject) => {
      const isOpen = this.bsModalRef !== null;
      if (isOpen) {
        resolve(this.bsModalRef);
      }
      if (id) {
        this.service.findOne(id).subscribe((p) => {
          this.bsModalRef = this.programModalRef(comp, p);
          resolve(this.bsModalRef);
        });
      } else {
        setTimeout(() => {
          this.bsModalRef = this.programModalRef(comp, {id: null, name: null});
          resolve(this.bsModalRef);
        }, 0);
      }
    });
  }

  programModalRef(comp: Component, p: any) {
    const initialState = {
      vm: p,
      title: 'Edit ',
      backdrop: 'static'
    };
    const bsModalRef = this.bsModalService.show(comp, {initialState});
    bsModalRef.content.closeBtnName = 'Close';
    bsModalRef.content.saveBtnName = p === null ? 'Save' : 'Update';
    // bsModalRef.result.then((result) => {
      this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
      this.bsModalRef = null;
    // }, (reason) => {
    //   this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
    //   this.bsModalRef = null;
    // });
    return bsModalRef;
  }
}
