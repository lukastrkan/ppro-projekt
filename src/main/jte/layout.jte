@param gg.jte.Content content
@param cz.trkan.pproprojekt.security.MyUserDetails user = cz.trkan.pproprojekt.utils.JteUtils.getCurrentUser()
@param org.springframework.validation.BindingResult bindingResult = null

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bootstrap 5 Layout</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex flex-column min-vh-100">
<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="/">Memes</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/upload">Upload</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/tags">Tags</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/categories">Categories</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                @if (user == null)
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/register">Register</a>
                    </li>
                @else
                    <li class="nav-item">
                        <a class="nav-link" href="#">Profile</a>
                    </li>
                    <li class="nav-item">
                        <form method="post" action="/logout">
                            <button type="submit" class="nav-link btn btn-link">Logout</button>
                        </form>
                    </li>
                @endif
            </ul>
        </div>
    </div>
</nav>

<!-- Main Content Area -->
<div class="container my-5 flex-grow-1">
    @if(bindingResult != null && bindingResult.hasErrors())
        <div class="alert alert-danger" role="alert">
            <ul class="mb-0">
                @for(org.springframework.validation.FieldError error : bindingResult.getFieldErrors())
                    <li>${error.getDefaultMessage()}</li>
                @endfor
            </ul>
        </div>
    @endif
    ${content}
</div>

<!-- Footer -->
<footer class="bg-light text-center text-lg-start mt-auto">
    <div class="text-center p-3">
        &copy; 2024 Skibidi Inc.
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
