import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CapteursComponent } from './components/capteurs/capteurs.component';
import { ObservationsComponent } from './components/observations/observations.component';
import { RecommandationsComponent } from './components/recommandations/recommandations.component';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'capteurs', component: CapteursComponent },
  { path: 'observations', component: ObservationsComponent },
  { path: 'recommandations', component: RecommandationsComponent }
];
