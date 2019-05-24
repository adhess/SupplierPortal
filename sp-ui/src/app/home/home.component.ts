import {Component, OnInit} from '@angular/core';
import {Label} from "ng2-charts";
import {ChartOptions, ChartType} from "chart.js";
import {HomeService} from "./home.service";
import {LoginService} from "../auth/login/login.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.sass']
})
export class HomeComponent implements OnInit {
  // Pie
  public pieChartOptions: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    legend: {
      position: 'right',
    },
    // plugins: {
    //   datalabels: {
    //     formatter: (value, ctx) => {
    //       const label = ctx.chart.data.labels[ctx.dataIndex];
    //       return label;
    //     },
    //   },
    // }
  };

  public pieChartLabelsInvoice: Label[] = ['En Cours', 'Comptabilisée', 'Refusée', 'Bloquée'];
  public pieChartLabelsOrder: Label[] = ['Transmise au fournisseur', 'Cloturé'];
  public pieChartDataInvoice: number[] = [];
  public pieChartDataOrder: number[] = [];
  public pieChartType: ChartType = 'pie';
  public pieChartLegend = true;
  public pieChartColorsInvoice = [
    {
      backgroundColor: ['rgba(10, 255, 31, 0.41)', 'rgba(28, 255, 247, 0.41)', 'rgba(255, 184, 55, 0.41)', 'rgba(255, 34, 21, 0.41)'],
    },
  ];
  public pieChartColorsOrder = [
    {
      backgroundColor: ['rgba(44, 255, 138, 0.41)', 'rgba(46, 186, 255, 0.41)'],
    },
  ];
  private data: any;

  constructor(private homeService: HomeService, public loginService: LoginService) {
    homeService.getInfoStatusCharts().subscribe(u => {
      this.data = u['body'];
      const tab = [];
      tab.push(this.data.invoice.ONGOING);
      tab.push(this.data.invoice.ACCOUNTED);
      tab.push(this.data.invoice.REFUSED);
      tab.push(this.data.invoice.BLOCKED);
      this.pieChartDataInvoice = tab;
      const tab1 = [];
      tab1.push(this.data.order.TRANSMITTED);
      tab1.push(this.data.order.CLOSED);
      this.pieChartDataOrder = tab1;
    });
  }


  ngOnInit() {
  }

}
