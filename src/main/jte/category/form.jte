@param cz.trkan.pproprojekt.model.Category category
@param org.springframework.validation.BindingResult bindingResult = null
@param String title = null

@template.layout(content = @`
    <div class="row justify-content-center">
        <div class="col-md-6">
                <h2 class="mb-4">@if (category.getId() > 0)Edit@elseCreate@endif Category</h2>
            <form method="post">
                <div class="mb-3">
                    <label for="categoryName" class="form-label">Category Name</label>
                    <input type="text" class="form-control" id="categoryName" name="name" value="${category.getName()}" required>
                </div>
                <button type="submit" class="btn btn-primary">@if (category.getId() > 0)Edit@elseCreate@endif Category</button>
                <a href="/categories" class="btn btn-secondary">Cancel</a>
            </form>
        </div>
    </div>
`, bindingResult = bindingResult, title = title)