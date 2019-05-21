import {Component, EventEmitter, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';

export interface PeriodicElementInvoice {
  devbruliq: any;
  devtvaliq: any;
  facqte: any;
  lignum: any;
  mvtlib: any;
  uniprx: any;
  uniprx1: any;
}


@Component({
  selector: 'app-affect-supplier',
  templateUrl: './affect-supplier.component.html',
  styleUrls: ['./affect-supplier.component.scss']
})
export class AffectSupplierComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  onAdd = new EventEmitter();

  tableHeader = [
    {name: 'credat', label: 'Date de création'},
    {name: 'supadr', label: 'Adresse'},
    {name: 'supcod', label: 'Code'},
    {name: 'supnom', label: 'Nom'},
    {name: 'suptyp', label: 'type'},
    {name: 'upddat', label: 'Modification'},
  ];
  displayedColumns: string[] =
    ['credat', 'supadr', 'supcod', 'supnom', 'suptyp', 'upddat'];
  dataSource;
  selectedRow;

  public data;
  public username;

  constructor(@Inject(MAT_DIALOG_DATA) public d) {
    this.data = d;
    this.dataSource = new MatTableDataSource(d.suppliers);
    this.username = d.username;
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


  onAffectSupplier() {
    this.onAdd.emit();
  }
}
