import {Component, EventEmitter, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-add-contact',
  templateUrl: './add-contact.component.html',
  styleUrls: ['./add-contact.component.scss']
})
export class AddContactComponent implements OnInit {
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;

  onAdd = new EventEmitter();
  form;
  authorities = [
    {name: 'readOrders', label: 'Voir Les Commandes'},
    {name: 'readAndWriteOrders', label: 'Voir Et Modifier Les Commandes'},
    {name: 'readInvoices', label: 'Voir Les Factures'},
    {name: 'readAndWriteInvoices', label: 'Voir Et Modifier Les Factures'},
    {name: 'readContacts', label: 'Voir Les Contactes'},
    {name: 'readAndWriteContacts', label: 'Voir Et Modifier Les Contactes'},
  ];

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.firstFormGroup = this.formBuilder.group({
      username: [''],
      password: [''],
      name: [''],
      telephone: [''],
      fax: [''],
      email: [''],
    });
    this.secondFormGroup = this.formBuilder.group({
      city: [''],
      state: [''],
      zipCode: [''],
    });
    this.thirdFormGroup = this.formBuilder.group({
      authorities: [''],
    });
  }

  onAddContact(form) {
    this.form = form;
    console.log(form);
    this.onAdd.emit();
  }

  onSubmit() {
    this.form = this.firstFormGroup.value;
    this.form['city'] = this.secondFormGroup.value['city'];
    this.form['state'] = this.secondFormGroup.value['state'];
    this.form['zipCode'] = this.secondFormGroup.value['zipCode'];
    this.form['authorities'] = this.thirdFormGroup.value['authorities'];
    // console.log(this.form);
    this.onAdd.emit();
  }
}
