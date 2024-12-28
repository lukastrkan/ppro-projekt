@param String error
@param String logout


@template.layout(content = @`
    <h1 class="text-center">Login</h1>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <form method="post">
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" placeholder="Enter your email">
                </div>
                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" class="form-control" id="username" placeholder="Enter your username">
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" placeholder="Enter your password">
                </div>
                <div class="mb-3">
                    <label for="password2" class="form-label">Password again</label>
                    <input type="password" class="form-control" id="password2" placeholder="Repeat your password">
                </div>
                <button type="submit" class="btn btn-primary w-100">Register</button>
                <div class="text-center mt-3">
                    <a href="/login ">Login</a>
                </div>
            </form>
        </div>
    </div>
`)