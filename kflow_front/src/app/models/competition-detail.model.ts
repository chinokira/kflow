export interface Competition {
  id: number;
  name: string;
  place: string;
  level: string;
  startDate: string;
  endDate: string;
  categories: Categorie[];
}

export interface Categorie {
  id: number;
  name: string;
  participants?: Participant[];
  stages: Stage[];
}

export interface Participant {
  id: number;
  bibNb: number;
  name: string;
  club: string;
  runs?: Run[];
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