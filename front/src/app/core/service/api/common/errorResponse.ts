import { throwError } from 'rxjs';

const getErrorMessageFromCatchedError = (error: any) => {
  console.log('error', error);
  const errorMessage = error.message || 'an error occurred';
  return throwError(() => new Error(errorMessage));
};

export default getErrorMessageFromCatchedError;
