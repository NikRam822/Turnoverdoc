import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogadminComponent } from './logadmin.component';

describe('LogadminComponent', () => {
  let component: LogadminComponent;
  let fixture: ComponentFixture<LogadminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LogadminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LogadminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
