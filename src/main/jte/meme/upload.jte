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
        <h2 class="mb-4">Upload Meme</h2>

        <form action="/upload" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="name" class="form-label">Meme Name</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
            <div class="mb-3">
                <label for="file" class="form-label">Meme Image</label>
                <input type="file" class="form-control" id="file" name="file" accept="image/*,video/*" required>
            </div>
            <div class="mb-3">
                <label for="category" class="form-label">Category</label>
                <select class="form-select" id="category" name="category" required>
                    @for(Category category : categories)
                        <option value="${category.getId()}">${category.getName()}</option>
                    @endfor
                </select>
            </div>
            <div class="mb-3">
                <label for="tags" class="form-label">Tags</label>
                <select class="form-select" id="tags" name="tags" multiple>
                    @for(Tag tag : tags)
                        <option value="${tag.getId()}">${tag.getName()}</option>
                    @endfor
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Upload Meme</button>
        </form>
    </div>

    <script>
        $(document).ready(function() {
            $('.form-select').select2({
                theme: "bootstrap-5",
                width: '100%',
                placeholder: $(this).data('placeholder'),
                closeOnSelect: false
            });
        });
    </script>
`, bindingResult = bindingResult, title = "Upload Meme")