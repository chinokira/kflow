export interface Competition {
  id: number;
  startDate: string;
  endDate: string;
  level: string;
  place: string;
  categories: Categorie[];
}

export interface Categorie {
  id: number;
  name: string;
  participants: Participant[];
  stages: Stage[];
}

export interface Participant {
  id: number;
  name: string;
  bibNb: number;
  club: string;
  runs: Run[];
}

export interface Run {
  id: number;
  duration: number;
  score: number;
  stageName: string;
}

export interface Stage {
  id: number;
  name: string;
  nbRun: number;
  rules: string;
} 