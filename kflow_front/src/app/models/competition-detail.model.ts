import { Categorie } from "./categorie.model";
export interface Competition {
  id: number;
  name: string;
  place: string;
  level: string;
  startDate: string;
  endDate: string;
  categories?: Categorie[]|[];
}

