export class Brewery {
    id: string;
    name: string;
    breweryType: string;
    address1: string | null;
    address2: string | null;
    address3: string | null;
    city: string;
    stateProvince: string;
    postalCode: string;
    country: string;
    longitude: string;
    latitude: string;
    phone: string;
    websiteUrl: string;
    state: string;
    street: string;
}

function parseBrewery(data: any): Brewery {
    return {
        id: data.id,
        name: data.name,
        breweryType: data.brewery_type,
        address1: data.address_1 || null,
        address2: data.address_2 || null,
        address3: data.address_3 || null,
        city: data.city,
        stateProvince: data.state_province,
        postalCode: data.postal_code,
        country: data.country,
        longitude: data.longitude,
        latitude: data.latitude,
        phone: data.phone,
        websiteUrl: data.website_url,
        state: data.state,
        street: data.street
    };
}