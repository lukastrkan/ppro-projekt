@param java.util.List<cz.trkan.pproprojekt.model.Meme> memes
@param org.springframework.validation.BindingResult bindingResult = null

@template.layout(
content = @`
    <div class="container">
        <div class="row mb-4">
            <div class="col">
                <h2>Memes</h2>
                <a href="/upload" class="btn btn-primary mb-3">Upload New Meme</a>
            </div>
        </div>

        <div class="row row-cols-1 row-cols-md-3 g-4">
            @for(cz.trkan.pproprojekt.model.Meme meme : memes)
                <div class="col">
                    <div class="card h-100">
                        @if(meme.getPath().contains("videos"))
                            <video class="card-img-top" style="max-height: 200px; object-fit: cover;" controls>
                                <source src="/${meme.getPath()}" type="video/mp4">
                                Your browser does not support the video tag.
                            </video>
                        @else
                            <img src="/${meme.getPath()}" class="card-img-top"
                                 style="height: 200px; object-fit: cover;"
                                 alt="${meme.getName()}">
                        @endif
                        <div class="card-body">
                            <h5 class="card-title">${meme.getName()}</h5>
                            <p class="card-text">
                                <small class="text-muted">Posted on ${meme.getCreated().toString()}</small>
                            </p>
                            <div class="mb-2">
                                @for(cz.trkan.pproprojekt.model.Tag tag : meme.getTags())
                                    <span class="badge bg-secondary">${tag.getName()}</span>
                                @endfor
                            </div>
                            <a href="/meme/${meme.getId()}" class="btn btn-primary">View Details</a>
                        </div>
                    </div>
                </div>
            @endfor
        </div>
    </div>
`
)
