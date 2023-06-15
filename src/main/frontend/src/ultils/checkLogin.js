

export function checkLogin() {
    const tokenString = sessionStorage.getItem('token');
    const testToken = JSON.parse(tokenString);

    // If the user is signed in, return the token
    if(testToken) {
        return testToken;
    }

    // If the user is not signed in, return null
    return null;
}