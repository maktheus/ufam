function g = medianSFilter(f, w)
% medianSFilter  2-D spatial median filtering with replicate padding.
%
% Syntax:
%   g = medianSFilter(f, w)
%
% Inputs:
%   f  - Input image (M x N grayscale or M x N x C colour).
%   w  - Neighbourhood size (scalar, odd integer, e.g. 3 -> 3x3 window).
%
% Output:
%   g  - Median-filtered image, double, scaled to [0,1].
%
% Behaviour:
%   * The input is first scaled to [0,1] (double).
%   * Replicate padding of floor(w/2) rows and columns is applied.
%   * For each pixel position (i,j), a w-by-w neighborhood is extracted,
%     all values are sorted, and the median is assigned to g(i,j).
%     Median filtering is a NON-LINEAR operation; it cannot be expressed
%     as a convolution/correlation.
%
% Example:
%   f = imread('Fig3.37(a).jpg');
%   g = medianSFilter(f, 3);   % 3x3 median filter
%
% Dependencies:
%   imagePad4e  (Project #4)
%
% Author: Pedro Victor dos Santos Oliveira
% Disciplina: Processamento Digital de Imagens - UFAM (2026)

    % ----- argument validation -----------------------------------------------
    if nargin < 2
        error('medianSFilter: two arguments (f, w) are required.');
    end
    if ~isscalar(w) || w < 1 || mod(w,2) == 0
        error('medianSFilter: w must be a positive odd scalar (e.g. 3, 5, 7...).');
    end

    % ----- scale input to [0,1] (double) ------------------------------------
    f_d = double(f);
    f_max = max(f_d(:));
    if f_max > 0
        f_d = f_d / f_max;
    end

    % ----- handle colour images channel by channel --------------------------
    [~, ~, nch] = size(f_d);
    if nch > 1
        g = zeros(size(f_d));
        for ch = 1:nch
            g(:,:,ch) = medianSFilter(f_d(:,:,ch), w);
        end
        return;
    end

    % ----- padding ----------------------------------------------------------
    [M, N] = size(f_d);
    r = floor(w / 2);
    fp = imagePad4e(f_d, r, r, 'replicate');

    % ----- sliding-window median --------------------------------------------
    g = zeros(M, N);
    for i = 1:M
        for j = 1:N
            patch = fp(i : i+w-1, j : j+w-1);  % extract wxw neighborhood
            g(i, j) = median(patch(:));          % compute median
        end
    end

end
