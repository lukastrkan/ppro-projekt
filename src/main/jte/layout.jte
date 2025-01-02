@param gg.jte.Content content
@param cz.trkan.pproprojekt.security.MyUserDetails user = cz.trkan.pproprojekt.utils.JteUtils.getCurrentUser()
@param org.springframework.validation.BindingResult bindingResult = null
@param String title = null

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        @if(title != null)
            ${title} - Memes
        @else
            Memes
        @endif
    </title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
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
