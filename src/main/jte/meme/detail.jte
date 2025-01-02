@import cz.trkan.pproprojekt.model.Comment
@import cz.trkan.pproprojekt.model.Meme
@import cz.trkan.pproprojekt.model.Tag

@param Meme meme
@param org.springframework.validation.BindingResult bindingResult = null
@param boolean canEdit = false

@template.layout(
content = @`
    <div class="container">
        <div class="row mb-4">
            <div class="col-md-8">
                <!-- Meme Content -->
                @if(meme.getPath().contains("videos"))
                    <video class="img-fluid" controls>
                        <source src="/${meme.getPath()}" type="video/mp4">
                        Your browser does not support the video tag.
                    </video>
                @else
                    <img src="/${meme.getPath()}" class="img-fluid" alt="${meme.getName()}">
                @endif
            </div>
            <div class="col-md-4">
                <!-- Meme Details -->
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h2>${meme.getName()}</h2>
                    @if(canEdit)
                        <div>
                            <a href="/meme/${meme.getId()}/edit" class="btn btn-sm btn-outline-primary me-2">Edit</a>
                            <a href="/meme/${meme.getId()}/delete" class="btn btn-sm btn-outline-danger"
                            onclick="return confirm('Are you sure you want to delete this meme?')">Delete</a>
                        </div>
                    @endif
                </div>
                <p class="text-muted">Posted on ${meme.getCreated().toString()}</p>

                <!-- Category -->
                <div class="mb-3">
                    <h5>Category</h5>
                    <a href="/category/${meme.getCategory().getId()}" style="text-decoration: none;">
                        <span class="badge bg-primary">${meme.getCategory().getName()}</span>
                    </a>
                </div>

                <!-- Tags -->
                <div class="mb-3">
                    <h5>Tags</h5>
                    @for(Tag tag : meme.getTags())
                        <a href="/tag/${tag.getId()}" style="text-decoration: none;">
                            <span class="badge bg-secondary">${tag.getName()}</span>
                        </a>
                    @endfor
                </div>

                <!-- Comments Section -->
                <div class="mb-4">
                    <h4>Comments</h4>
                    @if(meme.getComments().isEmpty())
                        <p class="text-muted">No comments yet</p>
                    @else
                        @for(Comment comment : meme.getComments())
                            <div class="card mb-2">
                                <div class="card-body">
                                    <p class="card-text">${comment.getText()}</p>
                                    <small class="text-muted">By @if(comment.getAuthor() != null) ${comment.getAuthor().getUsername()}@endif
                                        on ${comment.getCreated().toString()}</small>
                                </div>
                            </div>
                        @endfor
                    @endif
                </div>

                <!-- Comment Form -->
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Add a Comment</h5>
                        <form action="/meme/${meme.getId()}/comment" method="post">
                            <div class="mb-3">
                                <textarea class="form-control" name="text" rows="3" required></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Post Comment</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
`,
title = meme.getName())


