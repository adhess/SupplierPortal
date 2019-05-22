import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {ProgressSpinnerDialogComponent} from '../progress-spinner-dialog/progress-spinner-dialog.component';
import {EntityService} from './entity.service';
import {DetailsComponent} from '../details/details.component';
import {HttpClient} from '@angular/common/http';
import {LoginService} from '../auth/login/login.service';
import {NgxSpinnerService} from "ngx-spinner";


export interface PeriodicElementOrder {
  credat: string;
  dealivdat: string;
  devcod: string;
  devtvamnt: number;
  devbrumnt: number;
  epcnum: number;
  epmadrlib: string;
  explivdat: string;
  status: string;
}

const tableHeaderOrder = [
  {name: 'epcnum', label: 'N°commande'},
  {name: 'status', label: 'Status'},
  {name: 'credat', label: 'Date créat.'},
  {name: 'devtvamnt', label: 'Montant TVA'},
  {name: 'devbrumnt', label: 'Montant Hors TVA'},
  {name: 'dealivdat', label: 'Date de livr. souhaitée'},
  {name: 'explivdat', label: 'Date livr. Au plus tard'},
  {name: 'epmadrlib', label: 'Adresse de livraison'},
  {name: 'devcod', label: 'Devise'},
];
const displayedColumnsOrders: string[] =
  ['index', 'epcnum', 'status', 'credat', 'devtvamnt', 'devbrumnt', 'dealivdat', 'explivdat', 'epmadrlib', 'devcod'];

/************************************************************************/


export interface PeriodicElementInvoice {
  devBruMnt: string;
  communication: string;
  devcod: string;
  status: number;
  serepftet: number;
  escprc: number;
  docref: string;
  ndcflg: string;
  serecatet: string;
  sercpttie: string;
  echdat: string;
  devtvamnt: string;
  recdat: string;
  devnetmnt: string;
}

const tableHeaderInvoice = [
  {name: 'devBruMnt', label: 'Montant Hors TVA'},
  {name: 'communication', label: 'Communication'},
  {name: 'devcod', label: 'Devise'},
  {name: 'status', label: 'Status'},
  {name: 'serepftet', label: 'Serial'},
  {name: 'escprc', label: '% escompte'},
  {name: 'docref', label: 'Référence'},
  {name: 'ndcflg', label: 'ndcflg'},
  {name: 'serecatet', label: 'Serial de l\'avenant'},
  {name: 'sercpttie', label: 'Fournisseur'},
  {name: 'echdat', label: 'Date d\'échéance'},
  {name: 'devtvamnt', label: 'TVA'},
  {name: 'recdat', label: 'Date de réception'},
  {name: 'devnetmnt', label: 'Montant total'},
];
const displayedColumnsInvoices: string[] =
  ['index', 'communication', 'devcod', 'status', 'escprc',
    'docref', 'ndcflg', 'serecatet', 'devBruMnt', 'echdat', 'devtvamnt', 'recdat', 'devnetmnt'];

/************************************************************************/
let ELEMENT_DATA = [];


@Component({
  selector: 'app-entity',
  templateUrl: './entity.component.html',
  styleUrls: ['./entity.component.scss']
})
export class EntityComponent implements OnInit {
  dialogRef: MatDialogRef<ProgressSpinnerDialogComponent>;
  entitySize = 0;
  displayedColumns: string[] = [];
  numberPages = 10;
  actualPage = 1;
  size = 10;
  tableHeader = [];
  entityType;
  dataSource = new MatTableDataSource(ELEMENT_DATA);
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  entityLabel: string;
  private timezoneMap = {BST: 'GMT+0100'};


  constructor(private activatedRoute: ActivatedRoute, private entityService: EntityService,
              private spinnerDialog: MatDialog, public detailsDialog: MatDialog,
              private loginService: LoginService, private spinner: NgxSpinnerService) {
    activatedRoute.params.subscribe(v => {
      console.log(v);
      this.entityType = v.type;
      switch (v.type) {
        case 'invoice':
          this.entityLabel = 'Factures';
          this.entityService.host = '/API/p2p/auth/invoice';
          this.tableHeader = tableHeaderInvoice;
          this.displayedColumns = displayedColumnsInvoices;
          entityService.getEntitiesNumber(1).subscribe(u => {
              this.loginService.openSnackBar(u);
              this.entitySize = <number>u.body;
            }, e =>
              this.loginService.openSnackBar(e)
          );
          this.setNumberPage();
          break;

        case 'order':
          this.entityLabel = 'Commandes';
          this.entityService.host = '/API/p2p/auth/order';
          this.tableHeader = tableHeaderOrder;
          this.displayedColumns = displayedColumnsOrders;
          entityService.getEntitiesNumber(1).subscribe(u => {
            this.loginService.openSnackBar(u);
            this.entitySize = <number>u.body;
          }, e => this.loginService.openSnackBar(e));
          this.setNumberPage();
          break;
      }
    });
  }

  ngOnInit() {
  }

  setNumberPage() {
    this.entityService.getEntitiesNumber(this.size).subscribe(u => {
        this.loginService.openSnackBar(u);
        this.numberPages = <number>u.body;
        this.actualPage = 1;
        this.getEntities();
      }, e => this.loginService.openSnackBar(e)
    );
  }

  pageChange(event, eventType) {
    if ('number_per_page' === eventType) {
      this.setNumberPage();
    } else {
      switch (eventType) {
        case 'input_Page':
          if (this.actualPage < 1) {
            this.actualPage = 1;
          }
          if (this.actualPage > this.numberPages) {
            this.actualPage = this.numberPages;
          }
          break;
        case 'first_page':
          this.actualPage = 1;
          break;
        case 'navigate_before':
          if (this.actualPage > 1) {
            this.actualPage--;
          }
          break;
        case 'navigate_next':
          if (this.actualPage < this.numberPages) {
            this.actualPage++;
          }
          break;
        case 'last_page':
          this.actualPage = this.numberPages;
          break;
      }
      this.getEntities();
    }
  }

  getEntities() {
    this.showProgressSpinner();
    this.entityService.getEntities(this.actualPage - 1, this.size).subscribe(u => {
        switch (this.entityType) {
          case 'order':
            const tab: PeriodicElementOrder[] = [];
            let i = 0;
            for (const o of u.body['content']) {
              i++;
              if (o.root.status === '0') {
                o.root.status = 'Transmise au fournisseur';
              } else {
                o.root.status = 'Cloturé';
              }

              o.root.credat = this.formatDate(o.root.credat);
              o.root.dealivdat = this.formatDate(o.root.dealivdat);
              o.root.explivdat = this.formatDate(o.root.explivdat);
              tab.push(o.root);
            }
            ELEMENT_DATA = tab;
            break;
          case 'invoice':
            const tab1: PeriodicElementInvoice[] = [];
            for (const o of u.body['content']) {
              if (o.root.status === 'ONGOING') {
                o.root.status = 'En Cours';
              } else if (o.root.status === 'ACCOUNTED') {
                o.root.status = 'Comptabilisée';
              } else if (o.root.status === 'REFUSED') {
                o.root.status = 'Refusée';
              } else {
                o.root.status = 'Bloquée';
              }

              o.root.echdat = this.formatDate(o.root.echdat);
              o.root.recdat = this.formatDate(o.root.recdat);
              tab1.push(o.root);
            }
            ELEMENT_DATA = tab1;
            // console.log(ELEMENT_DATA);
            break;
        }
        this.dataSource = new MatTableDataSource(ELEMENT_DATA);
        this.dataSource.sort = this.sort;
        this.closeProgressSpinner();
        this.loginService.openSnackBar(u);
      },
      e => this.loginService.openSnackBar(e));
  }

  showProgressSpinner() {
    this.spinner.show();
    // this.dialogRef = this.spinnerDialog.open(ProgressSpinnerDialogComponent, {
    //   panelClass: 'transparent',
    //   disableClose: true
    // });
  }

  closeProgressSpinner() {
    this.spinner.hide();
    // this.dialogRef.close();
  }

  getRecord(row) {
    // console.log('row: ');
    // console.log(row);
    this.detailsDialog.open(DetailsComponent, {
      data: {
        row,
        header: this.tableHeader,
      }
    });
  }

  private formatDate(d) {
    console.log(d);
    if (d) {
      const date = new Date(this.reOrderDate(d));

      const month = date.getMonth() + 1;
      const day = date.getDate();
      const hours = date.getHours();
      const minutes = date.getMinutes();
      return (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + date.getFullYear()
        + ', ' + (hours < 10 ? '0' : '') + hours + ':' + (minutes < 10 ? '0' : '') + minutes;
    }
  }


  reOrderDate(str) {
    const re = /^(\w+) (\w+) (\d\d) (\d\d:\d\d:\d\d) (\w+) (\d\d\d\d)$/;
    return str.replace(re, ($0, day, month, date, time, zone, year) => {
      return day + ' ' + month + ' ' + date + ' ' + year + ' ' + time + ' ' + (this.timezoneMap[zone] || zone);
    });
  }

}
