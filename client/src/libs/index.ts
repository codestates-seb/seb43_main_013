/** 2023/05/09 - 테스트에서 사용할 타이머 - by 1-blue */
export const timer = (time: number) => new Promise((resolve) => setTimeout(() => resolve(1), time));

export * from "./validate";
