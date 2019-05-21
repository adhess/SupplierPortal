import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AffectSupplierComponent } from './affect-supplier.component';

describe('AffectSupplierComponent', () => {
  let component: AffectSupplierComponent;
  let fixture: ComponentFixture<AffectSupplierComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AffectSupplierComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AffectSupplierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
