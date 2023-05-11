interface label {
  label: string;
  id: number;
  bind: {
    value: string;
    onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  };
  valid?: boolean;
}

export default label;
