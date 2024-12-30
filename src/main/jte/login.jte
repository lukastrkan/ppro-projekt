@param String error
@param String logout

@template.layout(content = @`
        <h1 class="text-center">Login</h1>
        <div class="row justify-content-center">
            <div class="col-md-6">
                <form method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" name="username" id="username" placeholder="Enter your username">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" name="password" id="password" placeholder="Enter your password">
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Login</button>
                    <div class="text-center mt-3">
                        <a href="/register">Register</a>
                    </div>
                </form>
            </div>
        </div>
`)