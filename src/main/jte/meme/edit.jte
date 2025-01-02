@import cz.trkan.pproprojekt.model.Meme
@import cz.trkan.pproprojekt.model.Category
@import cz.trkan.pproprojekt.model.Tag
@import java.util.List

@param Meme meme
@param org.springframework.validation.BindingResult bindingResult = null
@param List<Category> categories
@param List<Tag> tags

@template.layout(
content = @`
    <div class="container">
        <h2 class="mb-4">Edit Meme</h2>

        <form action="/meme/${meme.getId()}/edit" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="name" class="form-label">Meme Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${meme.getName()}" required>
            </div>

            <div class="mb-3">
                @if(meme.getPath().contains("videos"))
                    <video class="img-fluid mb-3" style="max-height: 300px;" controls>
                        <source src="/${meme.getPath()}" type="video/mp4">
                    </video>
                @else
                    <img src="/${meme.getPath()}" class="img-fluid mb-3" style="max-height: 300px;"
                         alt="${meme.getName()}">
                @endif
            </div>

            <div class="mb-3">
                <label for="category" class="form-label">Category</label>
                <select class="form-select" id="category" name="category" required>
                    @for(Category category : categories)
                        @if(category.getId() == meme.getCategory().getId())
                            <option value="${category.getId()}" selected>
                                ${category.getName()}
                            </option>
                        @else
                            <option value="${category.getId()}">
                                ${category.getName()}
                            </option>
                        @endif
                    @endfor
                </select>
            </div>

            <div class="mb-3">
                <label for="tags" class="form-label">Tags</label>
                <select class="form-select" id="tags" name="tags" multiple>
                    @for(Tag tag : tags)
                        @if (meme.getTags().contains(tag))
                            <option value="${tag.getId()}" selected>
                                ${tag.getName()}
                            </option>
                        @else
                            <option value="${tag.getId()}">
                                ${tag.getName()}
                            </option>
                        @endif
                    @endfor
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Save Changes</button>
            <a href="/meme/${meme.getId()}" class="btn btn-secondary">Cancel</a>
        </form>
    </div>

    <script>
        $(document).ready(function () {
            $('.form-select').select2({
                theme: "bootstrap-5",
                width: '100%',
                placeholder: $(this).data('placeholder'),
                closeOnSelect: false
            });
        });
    </script>
`, bindingResult = bindingResult, title = "Edit Meme")