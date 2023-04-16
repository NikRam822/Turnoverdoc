import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminPanelDetailComponent } from './admin-panel-detail.component';

describe('AdminPanelDetailComponent', () => {
  let component: AdminPanelDetailComponent;
  let fixture: ComponentFixture<AdminPanelDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminPanelDetailComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminPanelDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
