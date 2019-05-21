import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatTableDataSource} from '@angular/material';

export interface PeriodicElementOrder {
  artlib: any;
  cmdqte: any;
  conlib1: any;
  conlib2: any;
  conlib3: any;
  conlib4: any;
  devbrumnt: any;
  devtvamnt: any;
  lignum: any;
}

const tableHeaderOrder = [
  {name: 'artlib', label: 'Désignation'},
  {name: 'cmdqte', label: 'Qt'},
  {name: 'conlib1', label: 'conlib1'},
  {name: 'conlib2', label: 'conlib2'},
  {name: 'conlib3', label: 'conlib3'},
  {name: 'conlib4', label: 'conlib4'},
  {name: 'devbrumnt', label: 'Montant HTVA DEV'},
  {name: 'devtvamnt', label: 'TVA'},
  {name: 'lignum', label: 'N°'},
];
const displayedColumnsOrders: string[] =
  ['lignum', 'artlib', 'cmdqte', 'conlib1', 'conlib2', 'conlib3', 'conlib4', 'devbrumnt', 'devtvamnt'];

/************************************************************************/


export interface PeriodicElementInvoice {
  devbruliq: any;
  devtvaliq: any;
  facqte: any;
  lignum: any;
  mvtlib: any;
  uniprx: any;
  uniprx1: any;
}

const tableHeaderInvoice = [
  {name: 'devbruliq', label: 'Mnt. Hors TVA'},
  {name: 'devtvaliq', label: 'TVA'},
  {name: 'facqte', label: 'Qt.'},
  {name: 'lignum', label: 'N°'},
  {name: 'mvtlib', label: 'Libellé mouvement'},
  {name: 'uniprx', label: 'prix unitaire'},
  {name: 'uniprx1', label: 'prix unitaire'},
];
const displayedColumnsInvoices: string[] =
  ['lignum', 'facqte', 'devbruliq', 'devtvaliq', 'mvtlib', 'uniprx', 'uniprx1'];

/************************************************************************/
let ELEMENT_DATA = [];


@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {
  displayedColumns: string[] = [];
  tableHeader = [];
  dataSource = new MatTableDataSource(ELEMENT_DATA);

  public data;

  constructor(@Inject(MAT_DIALOG_DATA) public d) {
    this.data = d;
    if (d.row.epcdet) {
      ELEMENT_DATA = d.row.epcdet;
      this.tableHeader = tableHeaderOrder;
      this.displayedColumns = displayedColumnsOrders;
      console.log('order');
      console.log(d.row.epcdet);
      this.dataSource = new MatTableDataSource(ELEMENT_DATA);
    }
    if (d.row.epfdet) {
      ELEMENT_DATA = d.row.epfdet;
      this.dataSource = new MatTableDataSource(ELEMENT_DATA);
      this.tableHeader = tableHeaderInvoice;
      this.displayedColumns = displayedColumnsInvoices;
      console.log('invoice');
    }
    // console.log(d);
  }


  ngOnInit() {
  }

}
