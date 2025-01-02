@param cz.trkan.pproprojekt.model.Tag tag
@param org.springframework.validation.BindingResult bindingResult = null
@param String title = null

@template.layout(content = @`
    <div class="row justify-content-center">
        <div class="col-md-6">
                <h2 class="mb-4">@if (tag.getId() > 0)Edit@elseCreate@endif Tag</h2>
            <form method="post">
                <div class="mb-3">
                    <label for="tagName" class="form-label">Tag Name</label>
                    <input type="text" class="form-control" id="tagName" name="name" value="${tag.getName()}" required>
                </div>
                <button type="submit" class="btn btn-primary">@if (tag.getId() > 0)Edit@elseCreate@endif Tag</button>
                <a href="/tags" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
`, bindingResult = bindingResult, title = title)