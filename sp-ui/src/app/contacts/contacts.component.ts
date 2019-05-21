import {Component, OnInit} from '@angular/core';
import {ContactsService} from './contacts.service';
import {MatDialog, MatSnackBar, MatTableDataSource} from '@angular/material';
import {AffectSupplierComponent} from '../affect-supplier/affect-supplier.component';
import {LoginService} from '../auth/login/login.service';
import {AddContactComponent} from './add-contact/add-contact.component';
import {DetailsComponent} from '../details/details.component';


export interface PeriodicElementInvoice {
  activitySector: any;
  email: any;
  name: any;
  telephone: any;
  username: any;
}


/************************************************************************/
let ELEMENT_DATA = [];

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.scss']
})
export class ContactsComponent implements OnInit {
  tableHeaderAdmin = [
    {name: 'fax', label: 'Fax'},
    {name: 'email', label: 'Email'},
    {name: 'name', label: 'Nom'},
    {name: 'telephone', label: 'Telephone'},
    {name: 'username', label: 'Login'},
  ];
  displayedColumnsAdmins: string[] =
    ['username', 'name', 'telephone', 'email', 'fax'];

  dataSourceAdmin;


  tableHeaderSupplier = [
    {name: 'fax', label: 'Fax'},
    {name: 'email', label: 'Email'},
    {name: 'name', label: 'Nom'},
    {name: 'telephone', label: 'Telephone'},
    {name: 'username', label: 'Login'},
    {name: 'activitySector', label: 'Secteur d\'activité'},
    {name: 'company_number', label: 'Numéro d\'entreprise'},
    {name: 'employeeNumber', label: 'Nombre d\'enmployée'},
    {name: 'tva', label: 'TVA'},
  ];
  displayedColumnsSuppliers: string[] =
    ['username', 'name', 'telephone', 'email', 'fax', 'activitySector', 'company_number', 'tva', 'affectSupplier'];

  dataSourceSupplier;


  tableHeaderContacts = [
    {name: 'fax', label: 'Fax'},
    {name: 'email', label: 'Email'},
    {name: 'name', label: 'Nom'},
    {name: 'telephone', label: 'Telephone'},
    {name: 'username', label: 'Login'},
  ];
  displayedColumnsContacts: string[] =
    ['username', 'name', 'telephone', 'email', 'fax'];

  dataSourceContact;

  admins = [];
  suppliers = [];
  suppliersSRM;
  contacts = [];

  constructor(private contactsService: ContactsService, private supplierSRMDialog: MatDialog,
              private loginService: LoginService,
              private detailsDialog: MatDialog) {
    if (this.loginService.isAdmin()) {
      contactsService.getAllUser().subscribe(u => {
        this.loginService.openSnackBar(u);
        for (const elt of u.body as Array<object>) {
          if (elt['role'].valueOf() === 'ADMIN'.valueOf()) {
            this.admins.push(elt);
          }
          if (elt['role'].valueOf() === 'SUPPLIER'.valueOf()) {
            this.suppliers.push(elt);
          }
          if (elt['role'].valueOf() === 'CONTACT'.valueOf()) {
            this.contacts.push(elt);
          }
        }
        this.dataSourceAdmin = new MatTableDataSource(this.admins);
        this.dataSourceSupplier = new MatTableDataSource(this.suppliers);
        this.dataSourceContact = new MatTableDataSource(this.contacts);
        console.log(this.contacts);
      }, e => this.loginService.openSnackBar(e));
      contactsService.getAllSupplierSRM().subscribe(u => {
        this.loginService.openSnackBar(u);
        const temp = [];
        for (const elt of u.body as Array<object>) {
          const obj = elt['root'];
          obj._id = elt['_id'];
          temp.push(obj);
        }
        this.suppliersSRM = temp;
        console.log(this.suppliersSRM);
      }, e => this.loginService.openSnackBar(e));
    } else if (this.loginService.isSupplier() || this.loginService.isContact()) {
      contactsService.getAllContacts().subscribe(u => {
        this.loginService.openSnackBar(u);
        this.dataSourceContact = new MatTableDataSource(u.body);
      }, e => this.loginService.openSnackBar(e));
    }


  }

  ngOnInit() {
  }

  public affectSupplier(event) {
    const supplierSRMDialog = this.supplierSRMDialog.open(AffectSupplierComponent, {
      data: {
        suppliers: this.suppliersSRM,
        username: event.username
      }
    });

    supplierSRMDialog.componentInstance.onAdd.subscribe(() => {
      const selectedRow = supplierSRMDialog.componentInstance.selectedRow;
      const username = supplierSRMDialog.componentInstance.username;
      this.contactsService.affectSupplier(username, selectedRow._id).subscribe(u => this.loginService.openSnackBar(u), e => this.loginService.openSnackBar(e));
    });
    // this.supplierSRMDialog.afterAllClosed.subscribe(result => {
    //   console.log(`Dialog result: ${result}`);
    // });
  }

  addContact() {
    const addContact = this.detailsDialog.open(AddContactComponent, {
      width: '700px',
    });
    addContact.componentInstance.onAdd.subscribe(() => {
      const form = addContact.componentInstance.form;
      this.contactsService.addContact(form).subscribe(u => {

        this.loginService.openSnackBar(u);
        console.log(u);
      }, e => {
        this.loginService.openSnackBar(e);
      });
    });
  }


}
