export interface BaseUser {
  firstName?: string;
  lastName?: string;
  email?: string,
  imageUrl?: string,
  publicId?: string;
}

export interface ConnectedUser extends BaseUser {
  authorities?: string[];
}
