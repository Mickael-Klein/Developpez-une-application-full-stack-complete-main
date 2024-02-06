import { throwError } from 'rxjs';

const getErrorMessageFromCatchedError = (error: any) => {
  const errorMessage = error.message || 'an error occurred';
  return throwError(() => new Error(errorMessage));
};

export default getErrorMessageFromCatchedError;
