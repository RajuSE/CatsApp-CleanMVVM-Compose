package youtube.devxraju.catsforever.data.remote.mapper

import youtube.devxraju.catsforever.data.remote.dto.CatsItem
import youtube.devxraju.catsforever.domain.models.CatBreedsResponseItem


fun CatsItem.toModel(): CatBreedsResponseItem{
    return CatBreedsResponseItem(
        adaptability = adaptability,
        affection_level = affection_level,
        alt_names = alt_names,
        cfa_url = cfa_url,
        child_friendly = child_friendly,
        description = description,
        dog_friendly = dog_friendly,
        energy_level = energy_level,
        experimental = experimental,
        grooming = grooming,
        hairless = hairless,
        health_issues = health_issues,
        hypoallergenic = hypoallergenic,
        id = id,
        indoor = indoor,
        intelligence = intelligence,
        lap = lap,
        life_span = life_span,
        name = name,
        natural = natural,
        origin = origin,
        rare = rare,
        reference_image_id = reference_image_id,
        short_legs = short_legs,
        social_needs = social_needs,
        stranger_friendly = stranger_friendly,
        suppressed_tail = suppressed_tail,
        temperament = temperament,
        vcahospitals_url = vcahospitals_url,
        vetstreet_url = vetstreet_url,
        vocalisation = vocalisation,
        wikipedia_url = wikipedia_url,
        quantity = quantity,
        price = price,
        isAddedToCart = isAddedToCart,
        isFavourited = isFavourited
    )
}