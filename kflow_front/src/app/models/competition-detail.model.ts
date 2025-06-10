export interface Competition {
  id: number;
  place: string;
  level: string;
  startDate: string;
  endDate: string;
  categories: Categorie[];
}

export interface Categorie {
  id: number;
  name: string;
  competition?: Competition;
  stages: Stage[];
  participants: Participant[];
}

export interface Stage {
  id: number;
  name: string;
  nbRun: number;
  rules: string;
  categorie?: Categorie;
  runs: Run[];
}

export interface Run {
  id: number;
  duration: number;
  score: number;
  stage?: Stage;
  participant?: Participant;
}

export interface Participant {
  id: number;
  name: string;
  bibNb: number;
  club?: string;
  categories: Categorie[];
  runs: Run[];
} 