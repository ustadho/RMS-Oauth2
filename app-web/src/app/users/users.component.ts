import { UserService } from './user.service';
import { Observable } from 'rxjs';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: Observable<any>;
  constructor(
    private userService: UserService
  ) { }

  ngOnInit() {
    this.loadAll();
  }

  loadAll() {
    this.userService.getAll().subscribe(data => {
      this.users = data.content;
      console.log('users', this.users);
    });
  }
}
